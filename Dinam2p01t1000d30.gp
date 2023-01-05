set title 'Distribuicao da falsa noticia(Runge-Kutta de ordem 4)'
set xlabel 'Numero dias'
set ylabel 'Populacao'
set xrange [1:30]
set yrange [1:1000]
set grid
set datafile separator ";"
plot for [col=2:4] 'Dinam2p01t1000d30.csv' using 0:col with lines title columnheader
