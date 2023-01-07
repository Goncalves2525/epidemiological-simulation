set title 'Distribuicao da falsa noticia(Euler)'
set xlabel 'Numero dias'
set ylabel 'Populacao'
set xrange [1:365]
set yrange [1:1000]
set grid
set datafile separator ";"
plot for [col=2:4] 'Rucam1p10t1000d365.csv' using 0:col with lines title columnheader
