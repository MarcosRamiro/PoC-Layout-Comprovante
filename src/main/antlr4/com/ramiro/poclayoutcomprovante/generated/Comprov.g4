grammar Comprov;

programa: expressao;

expressao: expressao op=( IGUAL | DIFERENTE ) expressao                                             #comparativo
 		  | expressao op=( MAIORIGUAL | MENORIGUAL | DIFERENTE | MAIOR | MENOR ) expressao          #relacional
          | expressao '?' expressao ':' expressao                                     				#if
          | expressao AND expressao                                     	            			#andExpr
          | expressao OR expressao                                     				                #orExpr
          | expressao MAIS expressao                                     				            #concatenar
          | NUMERO                                                                       			#numero
          | 'json' '{' expressao '}'                                                     			#json
          | 'capitalize' '{' expressao '}'                                                 			#capitalize
          | STRING                                                                        		    #string
          | BOOLEANO                                                                        		#booleano
          | '(' expressao ')'                                                         				#parenteses
          ;

BOOLEANO: ('true' | 'false');
STRING: '"' (ESC| .)*? '"';
NUMERO: (DIGITO+ | FLOAT);
IGUAL: '==' ;
MAIORIGUAL: '>=' ;
MENORIGUAL: '<=' ;
DIFERENTE: '!=' ;
MAIOR: '>' ;
MENOR: '<' ;
OR : '||';
AND : '&&';
MAIS: '+';

fragment FLOAT: DIGITO+ '.' DIGITO*;
fragment DIGITO: [0-9];
fragment ESC: '\\"'|'\\\\';

WS: [ \t\n\r ]+ -> skip ;