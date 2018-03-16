/******************************************************************************
 *  Name:    Henrique Cerquinho
 *  NetID:   9793700 (n USP)
 *  Precept: MAC0323
 *
 ******************************************************************************/

Programming Assignment 2: Deques and Randomized Queues


/******************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 *****************************************************************************/

Ambas RandQueue e Deque foram implementadas com listas duplamente ligada (com
next e previous) pois era a estrutura que me parecia mais conveniente ao ter
que lidar com remoção de itens, tanto aleatórios quanto escolhido, dessas filas.

/******************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 *****************************************************************************/

Randomized Queue:   ~  ((3*n) - 2)*(tamanho do item)  bytes

Deque:              ~  ((3*n) - 2)*(tamanho do item)  bytes

Onde n é o número de nós usados na estrutura.

Cada nó possui um item e duas referencias para outro nó -> 3*n
Dois nós não terão uma das referências (first e last) -> -2




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
Ao tentar remover o ultimo elemento da fila tanto na Deque quanto na RandQueue,
se tentarmos printar o estado atual delas após essa remoção veremos o item que
acabou de ser removido, que não deveria aparecer.
Fora isso, o iterador da RandQueue não itera os itens em ordem aleatória, como
pedido no enunciado em inglês, e sim de início a fim.

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

Nenhuma ajuda utilizada além de sites como stack overflow e afins.


/******************************************************************************
 *  Describe any serious problems you encountered.
 *****************************************************************************/

Não diria problemas sérios, mas a maior dificuldade foi dada pelo fato de eu
ter que aprender Java ao vivo fazendo esse EP, visto que não tinha visto em
nenhuma outra matéria, nem estudado por conta própria (que admito que foi um
erro). A grande maioria dos obstáculos encontrados ao longo do EP foram dados
por esse desconhecimento da linguagem (nomenclatura, comportamento de
estruturas, etc).


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 *****************************************************************************/

Foi uma boa experiência para iniciar minha jornada com Java desse semestre e
dos anos a virem pela frente, apesar dos erros bobos e frustrantes que aparecem
em todos EPs.
