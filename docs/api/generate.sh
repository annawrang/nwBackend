#!/usr/bin/env bash

API_VERSION=1.0
FILE_NAME=sisterside-v${API_VERSION}

raml2html -i raml/${FILE_NAME}.raml -o html/${FILE_NAME}.html

raml2md -i raml/${FILE_NAME}.raml -o md/${FILE_NAME}.md
