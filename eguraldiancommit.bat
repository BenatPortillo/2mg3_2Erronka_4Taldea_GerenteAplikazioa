@echo off
cd /d ../2mg3_2Erronka_4Taldea_eguraldia/

git checkout master


git pull origin master


git add .


git commit -m "Eguraldia aktualizatuta automatikoki"


git push origin master

