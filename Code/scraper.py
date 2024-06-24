import random


def getIdentifier():
    id = ''
    while len(id) < 5:
        i = random.randint(0, 61)
        if i >= 0 and i <=9:
            id += str(i)
        elif i>=10 and i <= 35:
            id += chr(i+55)
        else:
            id += chr(i+61)
    print(id)


getIdentifier()