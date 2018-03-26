/******************************************************************************
 *  Name:     Henrique Cerquinho
 *  NetID:    9793700
 *  Precept:  MAC0323
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Operating system: Ubuntu 16.04 LTS
 *  Compiler: javac
 *  Text editor / IDE: Atom
 *
 *  Have you taken (part of) this course before: parte
 *  Have you taken (part of) the Coursera course Algorithm, Part I:
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/

Programming Assignment 1: Percolation


/******************************************************************************
 *  Describe how you implemented Percolation.java. How did you check
 *  whether the system percolates?
 *****************************************************************************/
Usando a WQUF, fui olhando casa por casa e comparando com suas 4 vizinhas,
e adicionando elas no mesmo grupo da WQUF caso fossem ambas abertas ou
ambas fechadas. No final, comparei todas as casas da borda de cima com todas
da borda de baixo duas a duas, e se estivessem no mesmo grupo do WQUF e fossem
abertas, o sistema percolava.


/******************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for values values of n and T when implementing
 *  Percolation.java with QuickFindUF.java.
 *
 *  To do so, fill in the two tables below. Each table must have 4-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that takes less than 0.1 seconds.
 *****************************************************************************/

(keep T constant)
T = 20

 n          time (seconds)
------------------------------
20              0.215
40              5.886
50              17.553
60              43.233
80              176.78


(keep n constant)
n = 30

 T          time (seconds)
------------------------------
10              0.685
20              1.351
40              2.666
80              5.104
480             31.6

/******************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.0 * T^1.5
 *
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient to two significant digits.
 *
 *****************************************************************************/

QuickFindUF running time (in seconds) as a function of n and T:
~


/******************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 *****************************************************************************/

 (keep T constant)
 T = 50

  n          time (seconds)
 ------------------------------
 30              0.72
 40              2.063
 50              5.504
 60              11.972
 70              23.397
 100             101.72


(keep n constant)
n = 40

 T          time (seconds)
------------------------------
10              0.57
20              0.897
40              1.676
80              3.359
160             6.458
640             24.779


WeightedQuickUnionUF running time (in seconds) as a function of n and T:
~





/**********************************************************************
 *  How much memory (in bytes) does a Percolation object (which uses
 *  WeightedQuickUnionUF.java) use to store an N-by-N grid? Use the
 *  64-bit memory cost model from Section 1.4 of the textbook and use
 *  tilde notation to simplify your answer. Briefly justify your answers.
 *
 *  Include the memory for all referenced objects (deep memory).
 **********************************************************************/

/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
Certamente uma limitação relativamente grande é que, para escolher as casas
aleatoriamente nos trials, são usados apenas numeros aleatorios, e quando uma
casa que ja está aberta é escolhida, apenas ressorteamos os números, perdendo
tempo incalculável devido ao uso de numeros aleatórios.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
---

/******************************************************************************
 *  Describe any serious problems you encountered.
 *****************************************************************************/
O problema dos numeros aleatórios descrito acima

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 *****************************************************************************/
Achei interessante ver como imagens em baixa resolução funcionam com o esquema
da percolação.