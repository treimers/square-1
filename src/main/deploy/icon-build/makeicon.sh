#http://stackoverflow.com/questions/12306223/how-to-manually-create-icns-files-using-iconutil

if [ $# -ne 1 ]
then
	echo "Usage makeicon <name>"
	echo "Converts an icon from png to icns, bmp and ico format and different png sizes"
	echo "Where name is your icon name without extension"
	echo "Example: makeicon JPrefs"
	exit 0
fi

cd "$( dirname "${BASH_SOURCE[0]}" )"

export name=$1

# icns
mkdir ${name}.iconset
sips -z 16 16     ${name}.png --out ${name}.iconset/icon_16x16.png
sips -z 32 32     ${name}.png --out ${name}.iconset/icon_16x16@2x.png
sips -z 32 32     ${name}.png --out ${name}.iconset/icon_32x32.png
sips -z 64 64     ${name}.png --out ${name}.iconset/icon_32x32@2x.png
sips -z 128 128   ${name}.png --out ${name}.iconset/icon_128x128.png
sips -z 256 256   ${name}.png --out ${name}.iconset/icon_128x128@2x.png
sips -z 256 256   ${name}.png --out ${name}.iconset/icon_256x256.png
sips -z 512 512   ${name}.png --out ${name}.iconset/icon_256x256@2x.png
sips -z 512 512   ${name}.png --out ${name}.iconset/icon_512x512.png
cp ${name}.png ${name}.iconset/icon_512x512@2x.png
iconutil -c icns ${name}.iconset
rm -R ${name}.iconset

# bmp
sips -s format bmp ${name}.png -z 256 256 --out ${name}.bmp

# ico
sips -s format ico ${name}.png -z 128 128 --out ${name}.ico

# png
sips -s format png ${name}.png -z 64 64 --out ${name}_64x64.png
sips -s format png ${name}.png -z 128 128 --out ${name}_128x128.png
sips -s format png ${name}.png -z 256 256 --out ${name}_256x256.png

# dirs
mkdir -p ../package/mac
cp -v ${name}.icns ../package/mac
cp -v ${name}-background.png ../package/mac
cp -v ${name}.icns ../package/mac/${name}-volume.icns
cp -v ${name}.icns ../package/mac/${name}-volume.icns

mkdir -p ../package/linux
cp -v ${name}.png ../package/linux

mkdir -p ../package/windows
cp -v ${name}.ico ../package/windows
cp -v ${name}.bmp ../package/windows/${name}-setup.bmp
