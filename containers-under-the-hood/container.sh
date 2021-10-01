#!/bin/sh

memory=104857600 # 100 MB

while getopts "m:" flag; do
    case "${flag}" in
        m) memory=${OPTARG};;
    esac
done

echo Creating a new container...
# get PID of container process
pid=$$
wdir=/tmp/container/$pid
mkdir -p $wdir
cd $wdir

# create new root fs
mkdir -p bin dev etc proc sys

# download and set busybox
echo Downloading BusyBox...
wget https://busybox.net/downloads/binaries/1.21.1/busybox-$(uname -i) -q -O bin/busybox
chmod +x bin/busybox

# mount processes and devices
sudo mount -t proc /proc proc
touch dev/urandom
sudo mount --bind /dev/urandom dev/urandom

# set limits via cgroups
cgroupdir=/sys/fs/cgroup/memory/container$pid
sudo mkdir $cgroupdir
sudo su -c "echo $pid > $cgroupdir/tasks"
sudo su -c "echo $memory > $cgroupdir/memory.limit_in_bytes"

# start container
echo Starting the container...
sudo unshare -f -p --mount-proc=$PWD/proc \
    chroot . /bin/busybox sh

# clean up
echo Cleaning up...
# clean up cgroups
# to remove a task from a cgroup you must write its PID to the root tasks file
sudo su -c "echo $pid > $cgroupdir/../tasks"
sudo rmdir $cgroupdir
# clean up fs
sudo umount dev/urandom
sudo umount proc
rm -rf $wdir

echo Bye!
