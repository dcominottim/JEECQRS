JEECQRS
=======

JEECQRS aims to provide a suitable set of low-level infrastructure
services that are needed in any CQRS implementation.  It uses the
infrastructure provided by any JEE 6.0 (web-profile or full profile
for JMS messaging) compliant container.

It is recommended (but not necessary) to hide these infrastructure
services behind suitable [Facades](http://c2.com/cgi/wiki?FacadePattern)
in your own application.
