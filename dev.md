

`docker run -ti --rm -v $(pwd):/git --entrypoint /bin/sh alpine/git`


`docker run -ti --rm -v $(pwd):/wdir -w /wdir ubuntu bash`