#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void **readFile(char *filename, int col) {
	int i, j, k, cur_line, word_count, excess;
	char c;
    char prev;
	char buf[MAX_WORD_SIZE];
    int spaces[col/4];
	FILE *file;

	/* inicia a lista de dados */
	dados = malloc(sizeof(line*));
	dados[0] = criaLine(MAX_LINE_SIZE);

	/* abre o arquivo */
	file = fopen(filename, "r");

	if (!file) {
		printf("ERROR!INVALID FILE!\n");
		return NULL;
	}

    cur_line = 0;

	for (i = j = 0; (c = getc(file)) != EOF; i++, j++) {
        if (c != ' ' && c != '\n') {
            buf[i] = c;
        }
        else {
            word_count++;
            if (j > col) { /* se ultrapassou o limite de colunas */
                cur_line++;
                excess = j - col; /* o quanto excedeu o limite */
                j = 0;
                for (k = 0; k < excess; k++) {
                    spaces[(word_count - k - 2) % (word_count - 2)]++;
                }
                write(words, spaces);
                spaces[0..word_count-2] = 1;
                word_count = 0;

            }
    		if (c == ' ' || c == '\n') { /* fim de um elemento */
    			buf[i] = '\0'; /* fecha o buffer como string */
    		}
            if (c == prev && prev == '\n') { /* fim do paragrafo, save meeeee */

            }
        }
        prev = c;
	}
	fclose(file);
}












