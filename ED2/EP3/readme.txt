/******************************************************************************
 *  Name: Henrique Cerquinho
 *  NetID: 9793700
 *  Precept: MAC0323
 *
 *  Partner Name:
 *  Partner NetID:
 *  Partner Precept:
 *
 *  Hours to complete assignment (optional): many
 *
 ******************************************************************************/

Programming Assignment 4: Slider Puzzle


/******************************************************************************
 *  Explain briefly how you implemented the Board data type.
 *****************************************************************************/
A estrutura da Board continha uma matriz nxn para representar as casas do
tabuleiro, um int n para guardar facilmente o tamanha e um int para o numero
de movimentos realizados para chegar na board atual (a board em questão).

Todos os métodos giravam em torno dessa implementação (da board como uma
matriz).



/******************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 *****************************************************************************/
A Node era a estrutura mais simples, tendo apenas uma referencia para uma
Board, uma para outra Node parent, e um int de prioridade, ao inves de
numero de movimentos. Na inicialização da Node, é dada uma Board, e lá é
calculada e atribuida a prioridade para a Node.



/******************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method as function of the board size n? Recall that with order-of-growth
 *  notation, you should discard leading coefficients and lower-order terms,
 *  e.g., n log n or n^3.

 *****************************************************************************/

Description:
Para checar isso, checamos o numero total de inversões entre as peçcas, e para
isso checamos cada uma delas com todas as que estão à sua frente para vermos
se estão invertidas (a que vem antes ser maior que a que vem depois).


Order of growth of running time:
n^4



/******************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt even if xx > yy.
 *****************************************************************************/


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt     28           1.75       0.10
   puzzle30.txt     30           2.93       0.16
   puzzle32.txt     32       nao resolve    3.59
   puzzle34.txt     34                      0.56
   puzzle36.txt     36                      9.22
   puzzle38.txt     38                      4.60
   puzzle40.txt     40                      0.91
   puzzle42.txt     42                      14.22



/******************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 *****************************************************************************/
Com certeza escolheria uma função de prioridade melhor, vista a diferença na
velocidade nos testes. Escolher a função de manhattan praticamente anula todas
as necessidades extras de memoria e desempenho que a função de hamming exige,
o que gera um resultado muito melhor que um gerado por um computador 2x mais
rapido ou uma fila 2x mais rapida.



/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
Visto que havia uma função O(n^2) disponivel no site do princeton para achar
o numero de inversões e eu não consegui implementá-la, acho que se tivesse
conseguido, haveria uma grande melhoria no desempenho


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

/******************************************************************************
 *  Describe any serious problems you encountered.
 *****************************************************************************/

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 *****************************************************************************/
 Foi divertido, principalmente na hora que as coisas magicamente funcionaram.