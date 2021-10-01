# Containers under the hood

Simple shell script to create a BusyBox-based container under Linux with primitives chroot, namespaces, and cgroups.

## Usage

Must by run as a superuser.

```
./container.sh [-m <memory-limit-in-bytes>]
```

## Example

Run a new container with memory limited to 7 MB:

```
$ sudo ./container.sh -m 7340032
/ # busybox echo Hello from a container!
Hello from a container!
/ # busybox ls /
bin   dev   etc   proc  sys
/ # data=$(busybox head -c 7500000 /dev/urandom)
Killed
$
```

## License

MIT
