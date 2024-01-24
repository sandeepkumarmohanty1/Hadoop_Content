import sys

def findsquare(x):
    ''' Find the suare of input number
    Args:
    x:Any integer
    Return:
    The suare of the numbers
    '''
    return x*x
def greet():
    print("Hello...")
def find_even_or_odd(x):
    if x % 2 == 0:
        print("Even")
    else:
        print('Odd')

def Main():
    a = sys.argv[1]
    greet()    
    print(findsquare(int(a)))

if __name__ == '__main__':
    Main()
