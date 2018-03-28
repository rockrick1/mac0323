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
Usando a WQUF, sempre que open() era chamada, eu checava se as casas vizinhas
(nos 4 lados) já estavam abertas. Caso estivessem, juntava a posição a ser
aberta no grupo da vizinha, na wquf. Além disso, usei duas casas virtuais para
representar as bordas superior e inferior, onde todas as casas abertas da borda
superior eram conectadas à sua casa virtual, assim como as inferiores, na casa
virtual inferior. Finalmente, só checava se as duas casas virtuais estavam no
mesmo grupo da wquf para checar se percolava.


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
T = 10000

 n          time (seconds)
------------------------------
20              0.279
40              0.857
80              3.301
160             13.878
200             21.925


(keep n constant)
n = 100

 T          time (seconds)
------------------------------
400             0.276
800             0.464
1600            0.861
16000           8.547
40000           21.32


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
 T = 10000

  n          time (seconds)
 ------------------------------
 20              0.279
 40              1.102
 80              5.279
 120             14.759
 160             30.434


(keep n constant)
n = 100

 T          time (seconds)
------------------------------
200             0.207
400             0.394
800             0.751
16000           14.337
32000           28.612


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