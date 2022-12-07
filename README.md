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
- obs: os dados são armazenados nas partiçoes e, caso aja, index secundários.
- as solicitações para leitura ou gravação ao dynamodb, são feitas via requisição http e precisam ser autenticadas (sdks da aws fazem isso pra gente)

## Partição uso chave simples
- quando usamos uma primary key simples, ela se torna a partição
- por traz o dynamodb usa uma função que gera um hash, com base nessa chave
- com base no hash o dynamodb determina aonde salvará a informação

## Partição uso chave composta (chave de particao e chave de classificacao)
- funciona igual a chave simples, mas os dados são salvos próximos ou agrupados na mesma partição (com base no hash retornado pela função, que aplicou na chave composta)
- exemplo de criação de uma tabela com chave composta
```
{
    TableName : "Music",
    KeySchema: [
        {
            AttributeName: "Artist",
            KeyType: "HASH", //Partition key
        },
        {
            AttributeName: "SongTitle",
            KeyType: "RANGE" //Sort key
        }
    ],
    AttributeDefinitions: [
        {
            AttributeName: "Artist",
            AttributeType: "S"
        },
        {
            AttributeName: "SongTitle",
            AttributeType: "S"
        }
    ],
    ProvisionedThroughput: {       // Only specified if using provisioned mode
        ReadCapacityUnits: 1,
        WriteCapacityUnits: 1
    }
}
```
- no exemplo acima, estamos precisando a capacidade de leitura / gravação
- isso impacta na nossa carga de trabalho e no tipo de leitura / gravação (leitura altamente consistente, final consistente e transacional)
- sob demanda, indicada quando não conhecemos nossa demanda, não tempos essa preocupação

## Tipos de tabela
- o dynamodb oferece 2 tipos de tabela:
  - standard -> modelo padrão
  - Standard-Infrequent Access -> pouca utilizada, ideal para logs ou histórico