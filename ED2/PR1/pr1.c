#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void write(char **words, int spaces[], int word_count) {
	int i, j;
	for (i = 0; i < word_count; i++) {
		printf("%s", words[i]);
		/* printa os espaços necessarios */
		for (j = 0; j < spaces[i]; j++)
			printf(" ");
	}
	printf("\n");
}


void **readFile(char *filename, int col) {
	int i, j, k, cur_line, word_count, excess;
	char c;
    char prev;
	char buf[15];
    int *spaces;
	int max_words;
	char **words; /* buffer para as linhas */
	FILE *file;

	/* abre o arquivo */
	file = fopen(filename, "r");

	if (!file) {
		printf("ERROR!INVALID FILE!\n");
		return NULL;
	}

	max_words = 40; /* maximo de palavras numa linha */

	spaces = malloc(col*sizeof(int));
	words = malloc(sizeof(char**));
	for (i = 0; i < max_words; i++){
		words[i] = malloc(15*sizeof(char));
	}

	prev = '\0';
    cur_line = 0;
	word_count = 0;


	for (i = j = 0; c != EOF; i++, j++) {
		c = getc(file);
        if (c != ' ' && c != '\n') {
            buf[i] = c;
        }
        else {
			buf[i] = '\0'; /* fecha o buffer como string */
			printf("%d\n", word_count);
			printf("kek\n");
			strcpy(words[word_count], buf);
			printf("%s\n", words[word_count]);
            word_count++;
			buf[0] = '\0'; /* reseta o buffer */
			i = -1; /* i = 0 na prox iteração */

            if (j >= col) { /* se ultrapassou o limite de colunas */
                excess = j - col; /* o quanto excedeu o limite */
                j = 0;
                for (k = 0; k < excess; k++) {
                    spaces[(word_count - k - 2) % (word_count - 2)]++;
                }
				spaces[word_count - 1] = 0;
				/* escreve as parada */
                write(words, spaces, word_count);

				/* reseta td */
				for (k = 0; k < word_count - 2; k++)
                	spaces[k] = 1;
                word_count = 0;
				cur_line++;

            }
            if (c == prev && prev == '\n') { /* fim do paragrafo, save meeeee */
				continue;
            }
        }
        prev = c;
	}
	fclose(file);
	for (i = 0; i < max_words; i++)
		free(words[i]);
	free(words);
	free(spaces);
	return 0;
}

int main(int argc, char **argv) {
	readFile(argv[1], atoi(argv[2]));
	return 0;
}









