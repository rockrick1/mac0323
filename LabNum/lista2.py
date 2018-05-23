import math
import numpy as np

def LU(A):
    print("A:")
    printa(A)
    n = 4
    P = []
    L = []
    for i in range(n):
        linP = []
        linL = []
        for j in range(n):
            linP.append(0)
            linL.append(0)
        linP[i] = 1
        linL[i] = 1
        P.append(linP)
        L.append(linL)

    for pivot in range(n):
        # permuta de forma ao maior ficar na primeira linha do pivot
        max = pivot

        for k in range(pivot, n):
            if A[k][pivot] > A[max][pivot]:
                max = k
                A[pivot],A[max] = A[max],A[pivot]
                P[pivot],P[max] = P[max],P[pivot]

        max_val = A[pivot][pivot]

        # acha os coeficientes esubtrai da primeira linha
        for i in range(pivot + 1, n):
            coef = A[i][pivot] / max_val
            L[i][pivot] = coef

            # zera a primeira pos, mexendo no resto
            for j in range(pivot, n):
                A[i][j] -= coef * A[pivot][j]




    print("P:")
    printa(P)

    print("L:")
    printa(L)
    print("U:")
    printa(A)

def printa(M):
    for lin in M:
        print(lin)

def mult_vector(a, b):
    sum = 0
    for i in range(len(a)):
        sum += a[i] * b[i]
    return sum

def mult_vector_const(c,v):
    ret = []
    for k in v:
        ret.append(c*k)
    return ret

def sub_vector(a,b):
    ret = []
    for i in range(len(a)):
        ret.append(a[i] - b[i])
    return ret

def norma(a):
    norma = 0
    for k in a:
        norma += k*k
    return math.sqrt(norma)

def QR(A):
    # cria o conjunto de vetores a serem ortogonalizados
    printa(A)
    span = []
    for j in range(len(A[0])):
        v = []
        for i in range(len(A)):
            v.append(A[i][j])
        span.append(v)

    ort = []
    ort.append(span[0])
    print("QR")
    for i in range(1,len(span)):
        v = span[i]
        proj = v

        for x in ort:
            vx = mult_vector(v,x)
            xx = mult_vector(x,x)
            sub = mult_vector_const(vx/xx, x)
            # -= sub
            proj = sub_vector(proj,sub)

        v = sub_vector(x,proj)
        ort.append(v)

    for v in ort:
        # divide pela norma
        norma = np.linalg.norm(v)
        v = mult_vector_const(1/norma, v)
        print(v)

    # Q^t A = R
    R = np.zeros((len(ort), len(ort)))
    print(R)

    print("R:")
    # for i in range(len(ort)):
    #     for j in range(len(ort[i])):
    #         sum = 0
    #         for o in range(len(ort[i])):
    #             sum += ort[i][o] * A[o][i]
    #         a.append(m)
    #     R.append(a)
    print(np.matmul(ort,A))




A = [[3,1,4,5],
     [1,2,2,1],
     [0,3,4,3],
     [4,1,1,0]]

# LU(A)
QR(A)

