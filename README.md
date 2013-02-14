What Terrible Failure with SOA in JAX-WS ?
==========================================

Installation
------------

Just checkout the source via git clone ...

How to run ?
------------

Install Apache Maven 3 and run :

> mvn clean install

What's happenning ?
-------------------

There are 2 integration tests in [fr.gwallet.market.SoaFailsIT](https://github.com/gwallet/soa-wtf/blob/master/src/test/java/fr/gwallet/market/SoaFailsIT.java) :

 * The first ask for a potato request and is given a potato response.
 * The second ask for a carrot request and is given a potato response.

When using maven, a pre integration test goal launch a [SOAPUI](http://www.soapui.org/ "SOAPUI") mock service.
This mock service respond a goog answer for potato request, but a bad answer for carrot request.

What's wrong ?
--------------

The JAX-WS client can :

 * make a call to the service,
 * receive a bad answer that do not correspond to the request (a potato response in place of carrot response),
 * all without error,
 * not retrieving any information, because a potato will never be a carrot. Never.
