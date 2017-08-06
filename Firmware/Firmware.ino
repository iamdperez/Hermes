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

int parseCommandValue(char c, char byteOffset){
    char s[4];
    int i, x = 0;
    int offSet = byteOffset == 'H' ? 7 : 3;
    int limit = byteOffset == 'H' ? 4 : 0;
    for(i = offSet; limit <= i; i--){
        s[x] = ((c >>i)&0x01) == 0x01 ? '1' : '0';
        x++;
    }
    int result = parseBinaryStringToInt(s);
    return result;
}

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  // put your main code here, to run repeatedly:

}
