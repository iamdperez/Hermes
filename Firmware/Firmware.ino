#define OP_SETMODE 1
#define OP_SETVALUE 2
#define OP_GETVALUE 3

#define DIGITAL 1
#define ANALOG 0

#define VALUE_INPUT 1
#define VALUE_OUTPUT 0

#define VALUE_HIGH 1
#define VALUE_LOW 0

#define RESP_ERROR 3
#define RESP_SUCCESS 2
#define RESP_ON 1
#define RESP_OFF 0

#define COMMAND_TERMINATOR 59
#define HIGH_BIT_PART 1
#define LOW_BIT_PART 0

boolean commandComplete = false;
String command = String(2);

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

int parseCommandValue(char c, int byteOffset){
    char s[4];
    int i, x = 0;
    int offSet = byteOffset == HIGH_BIT_PART ? 7 : 3;
    int limit = byteOffset == HIGH_BIT_PART ? 4 : 0;
    for(i = offSet; limit <= i; i--){
        s[x] = ((c >>i)&0x01) == 0x01 ? '1' : '0';
        x++;
    }
    int result = parseBinaryStringToInt(s);
    return result;
}

void getIncomingBytes(){
    char inChar = Serial.read();
    if(in == COMMAND_TERMINATOR){
        commandComplete = true;
        Serial.flush();
    } else {
        command += inChar;
    }
}

void resetValues(){
    commandComplete = false;
    command.charAt(0) = '0';
    command.charAt(1) = '0';
}

int getCommandValueType(){
    return parseCommandValue(command.charAt(1),HIGH_BIT_PART);
}

int getPinNumber(){
    return parseCommandValue(command.charAt(1), LOW_BIT_PART);
}

int getCommandType(){
    return parseCommandValue(command.charAt(0), LOW_BIT_PART);
}

void setMode(){
    int type = getCommandValueType();
    int pin = getPinNumber();
    if(type == VALUE_INPUT){
        pinMode(pin,INPUT);
        Serial.write(RESP_SUCCESS);
    } else if(type == VALUE_OUTPUT){
        pinMode(pin,OUTPUT);
        Serial.write(RESP_SUCCESS);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void setValue(){
    int commandType = getCommandType();
    int valueType = getCommandValueType();
    int pin = getPinNumber();
    if(commandType == DIGITAL){
        if(valueType == VALUE_HIGH){
            digitalWrite(pin, HIGH);
        }else if(valueType == VALUE_LOW){
            digitalWrite(pin, LOW);
        }else {
            Serial.write(RESP_ERROR);
            return;
        }
        Serial.write(RESP_SUCCESS);
    }else if(commandType == ANALOG){
        analogWrite(pin, valueType);
        Serial.write(RESP_SUCCESS);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void getValue(){
    int commandType = getCommandType();
    int pin = getPinNumber();
    int value = 0;
    if(commandType == DIGITAL){
        value = digitalRead(pin);
        if(value == HIGH){
            Serial.write(RESP_ON);
        }else if(value == LOW){
            Serial.write(RESP_OFF);
        }else {
            Serial.write(RESP_ERROR);
        }
    }else if(commandType == ANALOG){
        value = analogRead(pin);
        Serial.write(value);
    }else{
        Serial.write(RESP_ERROR);
    }
}

void processCommand(){
    int op  = parseCommandValue(command.charAt(0),HIGH_BIT_PART);
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
