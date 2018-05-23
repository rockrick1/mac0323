import math
import numpy as np
np.set_printoptions(precision=2,suppress=True)

def LU(A):
    print("A:")
    print(A)
    n = len(A)
    P = np.identity(n)
    L = np.zeros((n,n))

    for pivot in range(n-1):
        # permuta de forma ao maior ficar na primeira linha do pivot
        max = pivot

        # acha o maior valor para ser o pivot
        for k in range(pivot, n):
            if A.item(k,pivot) > A.item(max,pivot):
                max = k
                # troca as linhas
        A[[pivot,max]] = A[[max,pivot]]
        P[[pivot,max]] = P[[max,pivot]]
        L[[pivot,max]] = L[[max,pivot]]

        max_val = A.item(pivot,pivot)

        # acha os coeficientes e subtrai da primeira linha
        for i in range(pivot + 1, n):
            coef = A.item(i, pivot) / max_val
            L[i, pivot] = coef

            # zera a primeira pos, mexendo no resto
            for j in range(pivot, n):
                # A[i] -= np.multiply(coef,A[pivot])
                A[i,j] -= coef * A.item(pivot,j)

    for i in range(n):
        L[i,i] = 1

    print("P:")
    print(P)
    print("L:")
    print(L)
    print("U:")
    print(A)
    print("LxU")
    B = np.matmul(L,A)
    print(np.matmul(P,B))





def QR(A):
    # cria o conjunto de vetores a serem ortogonalizados
    print(A)
    span = []
    for j in range(len(A[0])):
        v = []
        for i in range(len(A)):
            v.append(A[i][j])
        span.append(v)

    ort = []
    print("QR")
    for i in range(len(span)):
        v = span[i]
        proj = span[i]

        for x in ort:
            vx = np.dot(span[i],x)
            xx = np.dot(x,x)
            sub = np.multiply(vx/xx, x)
            # -= sub
            proj = np.subtract(proj,sub)

        ort.append(proj)

    for i in range(len(ort)):
        # divide pela norma
        norma = np.linalg.norm(ort[i])
        ort[i] = np.multiply(1/norma, ort[i])

    # R = np.zeros((len(ort), len(ort)))

    # Q^t.A = R
    print("R:")
    R = np.matmul(ort,A)
    print(R)



def LLT(A):
    print(A)
    n = len(A)
    L = np.zeros((n,n))
    for k in range(n):
        for i in range(k+1):
            a = A.item(k,i)

            if k != i:
                sum = 0
                for j in range(i):
                    sum += L.item(i,j) * L.item(k,j)
                L[k,i] = (a - sum) / L.item(i,i)

            else:
                sum = 0
                for j in range(k-1):
                    sum += L.item(k,j) ** 2
                print(a - sum)
                L[k,k] = np.sqrt(a - sum)
    print("L:\n",L)
    print("Lt:\n",L.transpose())
    print(np.matmul(L,L.transpose()))

def main():
    A = np.matrix('2. 1 5; 1 4 4; 5 4 20')

    ## DONE
    # LU(A)

    ## DONE
    # QR(A)

    LLT(A)
main()
