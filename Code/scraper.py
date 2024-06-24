import random


def getIdentifier():
    str = ''
    while str.len() < 5:
        i = random.randint(0, 63)
        if i >= 0 and i <=9:
            str += i
        elif i>=10 and i <= 36:
            str += chr(i+55)
        else:
            str += chr(i+61)
    print(str)


getIdentifier()