# Olho no Orçamento
Sistema de tratamento, publicação e visualização dos dados do orçamento federal, em especial Convênios e Transferências

Protótipo do sistema FUNCIONANDO em: http://ono.dadosabertosbr.org
Vídeo descritivo do projeto em: http://ono.dadosabertosbr.org/video

*ATENÇÃO*: a branch principal do projeto é KELLYTON

Este é o repositório de proposta do sistema Olho No Orçamento, que foi submetido para a Hackathon de Combate a Corrupção promovida pelo Ministério da Justiça. Os objetivos do projeto são:
a) Tradução dos dados de convênios e transferências para uma linguagem acessível ao cidadão: Principalmente ao mostrar ao cidadão gastos que foram executados próximos ao mesmo, com localização no mapa;

b) Possibilidade de seleção, por parte do cidadão, das informações que impactem seu cotidiano: através do detalhamento dos convênios e pagamentos de convênios nas suas proximidades;

c) c) Aumento da participação popular no acompanhamento e fiscalização da execução física das políticas públicas implementadas por meio de convênios e transferências: visto que o cidadão está próximo ao local de execução do convênio, o mesmo pode detectar alguma irregularidade e realizar uma denúncia;

d) Utilização, por parte governo, da informação coletiva a ser gerada pela solução: ao permitir a agregação de denúncias realizadas pelos cidadãos e encaminha-las aos órgãos competentes.

O projeto começou a ser desenvolvido em 10 de abril de 2016, e é baseado na experiência do premiado Laboratório de Dados Abertos Brasil (www.dadosabertosbr.org) no desenvolvimento de soluções utilizando dados abertos. Atualmente o protótipo funcional está hospedado em ono.dadosabertosbr.org. Todos os dados do protótipo funcional são reais, apesar de faltarem alguns dados (uma melhoria no extractor é necessária).

O sistema tem 3 branches:
- kellyton: de responsabilidade de Kellyton Brito, contém o código do servidor e a versão web
- api: de responsabilidade de Bruno Marques, publica uma API para ser utilizada por clientes mobile
- master*: utilizada por Angélica Lopes para um protótipo de aplicação Android. Ainda estamos decidindo se vamos fazer nativo ou não

*Ok, sabemos que a master não deveria ser usada assim, mas começamos o projeto a pouco tempo e Angélica não tem muita experiência com o github. Melhoraremos em breve.

# Para executar
Requisitos:
	- Java 7 ou superior
	- Play framework 2.1.3 ou superior (https://www.playframework.com/)
	- Executar: Acessar a pasta do servidor: /server/ONOServer e rodar: "play start". Irá subir na porta 9000
Falta o banco de dados. Para gerar o banco é necessário baixar as tabelas de convênios e pagamentos (01_ConveniosProgramas.csv e 21_PlanoAplicacaoPT.csv), 
descomentar e rodar os métodos do ExtractorController de forma semi assistida. Também é necessário gerar chaves das API's do google, que possuem limitação de acesso diário.
Conhecimento de parsing e da Google Geocode API é necessário.