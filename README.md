# TrabProgConc

Trabalho da Disciplina de Programação Concorrente.
Aplicativo android nativo em linguagem Java.

---------------------------------------------------

Simulação de dinâmica de uma população


- Objetivo:

O objetivo do trabalho consiste na implementação de um pequeno ecossistema formado por tubarões, focas, peixes e algas.


- Descrição:

Depois de iniciar o programa, deverá ser apresentado um menu que permita adicionar ao ambiente os seguintes tipos de indivíduos: tubarões, focas, peixes e algas. O usuário poderá adicionar o número que desejar de tubarões, focas, peixes e algas.

Os tubarões, focas e peixes deverão ser gerados em posições aleatórias e deverão se movimentar aleatoriamente na tela, sem atravessar as bordas. Já as algas deverão ser geradas aleatoriamente no ambiente e deverão ficar em posições fixas.

A quantidade de indivíduos e o tempo de simulação devem ser apresentados na tela. Os indivíduos deverão ser implementados como threads e compartilharão de um mesmo ambiente (como uma matriz, por exemplo).

A cada tubarão é a associado um contador de calorias que é decrementado de tempos em tempos (os valor do decremento e do tempo podem ser definidos pelo grupo), sendo que o tubarão morre quando o contador chegar a 0. Quando um tubarão fizer contato com uma foca ou com um peixe, o seu contador de calorias é incrementado e a foca ou o peixe morrem. O número de inicial calorias deverá ser fornecido pelo usuário no início do programa e valor do incremento de calorias pode ser devido pelo grupo.

As focas seguem o mesmo ciclo de vida de um tubarão (mesmo número de calorias dos tubarões), sendo que essas morrem de fome se não obterem comida (peixes). Quando um foca e um peixe fizerem contato, o peixe morre e o contador de calorias da foca é incrementado.

Por fim, os peixes também seguem o mesmo ciclo de vida dos tubarões e focas, ou seja, possuem o mesmo número de calorias e morrem de fome se não obterem comida (algas). Assim que um peixe comer uma alga, seu contador de calorias é incrementado.


- Observações:

Linguagens permitidas: Java, C#, C/C++, Python
