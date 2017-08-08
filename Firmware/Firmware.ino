#define OP_SETMODE 1
#define OP_SETVALUE 2
#define OP_GETVALUE 3

#define DIGITAL 1
#define ANALOG 0

#define VALUE_INPUT 0
#define VALUE_OUTPUT 1
#define VALUE_HIGH 2
#define VALUE_LOW 3

#define RESP_ERROR 3
#define RESP_SUCCESS 2
#define RESP_ON 1
#define RESP_OFF 0

#define COMMAND_TERMINATOR 59 //;
#define COMMAND_START 36 //$

#define HIGH_BIT_PART 1
#define LOW_BIT_PART 0

boolean commandComplete = false;
boolean startCopy = false;
char command [3];
int commandOffset = 0;

int parseBinaryStringToInt(char * s){
    char* currentOffset = &s[0];
    int result = 0;
    while(*currentOffset){
        result <<= 1;
        if(*currentOffset++ == '1')
            result ^= 1;
    }
    return result;
}

int parseCommandValue(char c, int bitOffset){
    char s[4];
    int i, x = 0;
    int offSet = bitOffset == HIGH_BIT_PART ? 7 : 3;
    int limit = bitOffset == HIGH_BIT_PART ? 4 : 0;
    for(i = offSet; limit <= i; i--){
        s[x] = ((c >>i)&0x01) == 0x01 ? '1' : '0';
        x++;
    }
    int result = parseBinaryStringToInt(s);
    return result;
}

void getIncomingBytes(){
    char inChar = Serial.read();
    if(inChar == COMMAND_TERMINATOR){
        commandComplete = true;
        startCopy = false;
        commandOffset = 0;
    } else if(startCopy) {
        command[commandOffset] = inChar;
        commandOffset++;
    }else if(inChar == COMMAND_START && !startCopy){
      startCopy = true;
      commandOffset = 0;
    }
}

void resetValues(){
    commandComplete = false;
    startCopy = false;
    command[0] = '0';
    command[1] = '0';
    command[2] = '0';
}

int getCommandValue(){
    return command[1];
}

int getPinNumber(){
    return command[2];
}

int getOperationType(){
    return parseCommandValue(command[0], LOW_BIT_PART);
}

int getOperation(){
    return parseCommandValue(command[0],HIGH_BIT_PART);
}

void setMode(){
    int value = getCommandValue();
    int pin = getPinNumber();
    if(value == VALUE_INPUT){
        pinMode(pin,INPUT);
        Serial.write(RESP_SUCCESS);
    } else if(value == VALUE_OUTPUT){
        pinMode(pin,OUTPUT);
        Serial.write(RESP_SUCCESS);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void setValue(){
    int opType = getOperationType();
    int value = getCommandValue();
    int pin = getPinNumber();
    if(opType == DIGITAL){
        if(value == VALUE_HIGH){
            digitalWrite(pin, HIGH);
        }else if(value == VALUE_LOW){
            digitalWrite(pin, LOW);
        }else {
            Serial.write(RESP_ERROR);
            return;
        }
        Serial.write(RESP_SUCCESS);
    }else if(opType == ANALOG){
        analogWrite(pin, value);
        Serial.write(RESP_SUCCESS);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void getValue(){
    int opType = getOperationType();
    int pin = getPinNumber();
    int value = 0;
    if(opType == DIGITAL){
        value = digitalRead(pin);
        if(value == HIGH){
            Serial.write(RESP_ON);
        }else if(value == LOW){
            Serial.write((byte)RESP_OFF);
        }else {
            Serial.write(RESP_ERROR);
        }
    }else if(opType == ANALOG){
        value = analogRead(pin);
        Serial.write(value);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void processCommand(){
    int op  = getOperation();
    switch(op){
        case OP_SETMODE:
            setMode();
        break;
        case OP_SETVALUE:
            setValue();
        break;
        case OP_GETVALUE:
            getValue();
        break;
        default:
            Serial.write(RESP_ERROR);
    }
    resetValues();
    Serial.flush();
}

void setup() {
  Serial.begin(9600);
  Serial.flush();
}

void loop() {
    if(Serial.available() > 0){
        getIncomingBytes();
    }

    if(commandComplete == true){
        processCommand();
    }

}
