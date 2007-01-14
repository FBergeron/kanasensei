#!/bin/sh
echo Enter latest revision number.
read rev
mkdir kanasensei-r$rev-svn-backup
rsync -av kanasensei.svn.sourceforge.net::svn/kanasensei/* kanasensei-r$rev-svn-backup
tar zcf kanasensei-r$rev-svn-backup.tar.gz kanasensei-r$rev-svn-backup
rm -rf kanasensei-r$rev-svn-backup
