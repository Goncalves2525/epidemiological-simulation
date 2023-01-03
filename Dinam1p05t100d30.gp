set title 'Distribuicao da falsa noticia(Euler)'
set xlabel 'Numero dias'
set ylabel 'Populacao'
set xrange [1:30]
set yrange [1:100]
set grid
set datafile separator ";"
plot for [col=2:4] 'Dinam1p05t100d30.csv' using 0:col with lines title columnheader
