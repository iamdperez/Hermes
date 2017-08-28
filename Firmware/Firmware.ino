#define OP_SETMODE 1
#define OP_SETVALUE 2
#define OP_GETVALUE 3
#define OP_GETARDUINO_MODEL 4

#define VALUE_INPUT 0
#define VALUE_OUTPUT 1
#define VALUE_HIGH 0
#define VALUE_LOW 1

#define RESP_ERROR 3
#define RESP_SUCCESS 2
#define RESP_ON 1
#define RESP_OFF 0

#define COMMAND_TERMINATOR 0x7e //~
#define COMMAND_START 0x7f      //DEL

#include "ArduinoBoardManager.h"


ArduinoBoardManager arduino = ArduinoBoardManager();
boolean commandComplete = false;
boolean startCopy = false;
char command[2];
int commandOffset = 0;

void getIncomingBytes()
{
    char inChar = Serial.read();
    if (inChar == COMMAND_TERMINATOR)
    {
        commandComplete = true;
        startCopy = false;
        commandOffset = 0;
    }
    else if (startCopy)
    {
        command[commandOffset] = inChar;
        commandOffset++;
    }
    else if (inChar == COMMAND_START && !startCopy)
    {
        startCopy = true;
        commandOffset = 0;
    }
}

void resetValues()
{
    commandComplete = false;
    startCopy = false;
    command[0] = '0';
    command[1] = '0';
}

int getPinNumber()
{
    return (byte)command[1];
}

int getOperationType()
{
    return command[0] & 0x0f;
}

int getOperation()
{
    return command[0] >> 4;
}

void setMode()
{
    int value = getOperationType();
    int pin = getPinNumber();
    if (value == VALUE_INPUT)
    {
        pinMode(pin, INPUT);
        Serial.write(RESP_SUCCESS);
    }
    else if (value == VALUE_OUTPUT)
    {
        pinMode(pin, OUTPUT);
        Serial.write(RESP_SUCCESS);
    }
    else
    {
        Serial.write(RESP_ERROR);
    }
}

void setValue()
{
    int value = getOperationType();
    int pin = getPinNumber();
    if (value == VALUE_HIGH)
    {
        digitalWrite(pin, HIGH);
    }
    else if (value == VALUE_LOW)
    {
        digitalWrite(pin, LOW);
    }
    else
    {
        Serial.write(RESP_ERROR);
        return;
    }
    Serial.write(RESP_SUCCESS);
}

void getValue()
{
    int pin = getPinNumber();
    if (digitalRead(pin) == HIGH)
    {
        Serial.write(RESP_ON);
    }
    else if (digitalRead(pin) == LOW)
    {
        Serial.write((byte)RESP_OFF);
    }
    else
    {
        Serial.write(RESP_ERROR);
    }
}

void getArduinoModel(){
    Serial.print(arduino.BOARD_NAME);
}

void processCommand()
{
    int op = getOperation();
    switch (op)
    {
    case OP_SETMODE:
        setMode();
        break;
    case OP_SETVALUE:
        setValue();
        break;
    case OP_GETVALUE:
        getValue();
        break;
    case OP_GETARDUINO_MODEL:
        getArduinoModel();
        break;
    default:
        Serial.write(RESP_ERROR);
    }
    resetValues();
}

void setup()
{
    Serial.begin(9600);
    Serial.flush();
}

void loop()
{
    if (Serial.available() > 0)
    {
        getIncomingBytes();
        Serial.flush();
    }

    if (commandComplete == true)
    {
        processCommand();
    }
}
