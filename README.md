# Projeto Gateway API
Neste projeto criei dois microservicos 

### Boleto Payment
Neste servico possuimos 2 metodos, primeiro para efetuar a transacao de geracao do boleto
e o segundo para obter status do boleto gerado

### CreditCard Payment
Neste servico possuimos 2 metodos, primeiro para efetuar a transacao de pagamento via cartao de credito
e o segundo para obter status do boleto gerado


####Tecnologias utilizadas
- Spring boot
- Elasticsearch
- docker

### Melhorias
- Criar topic em kafka para tratamento do transacional e processamento da captura.
- Implementacao de autenticacao via oauth2


Coloquei os docker files em /{nome_projeto}/src/main/resources/docker

