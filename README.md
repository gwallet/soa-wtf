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

When using maven, a pre integration test goal launch a [SOAPUI](http://www.soapui.org/ "SOAPUI") mock service corresponding [to this contract](https://github.com/gwallet/soa-wtf/blob/master/src/main/wsdl/market.wsdl).

With this contract, a _market_ WebService is supposed to wait after _potato_ or _carrot_ requests.

The mocked WebService respond a _good_ answer for any potato requests, but a ___bad___ answer for carrot request.
In fact, any carrot request will receive a potato response. This mock is a very bad WebService.

What's wrong ?
--------------

The JAX-WS client can :

 * make a call to the service,
 * receive a bad answer that do not correspond to the request (a potato response in place of carrot response),
 * all without error,
 * not retrieving any information, because a potato will never be a carrot. Never.

Why ?
-----

Because, when debugging an application in production mode, we saw bad behavior, quite impossible behavior.

Showing the log, messages seems to be inconcistant. Request of type A is answered with type B.
Today, we can not explain why the answered was randomly delivered, but when we force by mocking, we was very confuse about the fact that the WS client stack never complains.

And now ?
---------

Nothing at the moment of writing this lines.

I was thinking that SOAP was a trustful stack with XML schema validation, and contract coupling.
Now I'm wondering if it's not another boilerplate stack.
