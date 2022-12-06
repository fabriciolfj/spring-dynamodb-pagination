# spring-dynamodb-pagination
- Para saber mais sobre overloading de index no dynamodb https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-gsi-overloading.html

## Alguns conceitos do dynamodb
- temos alguns tipos de chaves primárias:
  - chave simples -> onde o chave fornecida é a partição aonde será armazenada (por traz o dynamodb gera um hash da chave, e o resultado é o local de armazenamento)
  - chave composta -> aonde possui a chave e a chave de ordenação
- alem das chaves, posso criar o index secundários, e projetar quais atributos serão retornados.
- existe 2 tipos de index secundários:
  -  global -> quando o index composto (chave + chave de ordenação), é diferente da chave principal
  - local -> quando o index composto, chave é iqual a chave principal mas o sort (ordenação) é diferente