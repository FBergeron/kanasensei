echo "Enter release (e.g.: 1.4): "
read rel
echo $rel

mkdir kanasensei-$rel
cp dist/public_html/index.html kanasensei-$rel
mkdir kanasensei-$rel/res
cp dist/public_html/res/*.jar kanasensei-$rel/res
cp dist/public_html/res/f*.png kanasensei-$rel/res
cp dist/public_html/res/japanearth.jpg kanasensei-$rel/res
tar -cf kanasensei-$rel.tar kanasensei-$rel
gzip kanasensei-$rel.tar
exit 0
