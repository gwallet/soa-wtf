package fr.gwallet.market;

import org.junit.Before;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @param <S> Service contract.
 * @param <P> PortType contract.
 */
public abstract class AbstractWebServiceIntegrationTestCase<S, P> implements SOAPHandler<SOAPMessageContext> {

    private P port;

    @Before
    public void setUp() throws Exception {
        port = initialize(getPort(getService()));
    }

    private P initialize(P port) {
        Binding binding = ((BindingProvider) port).getBinding();
        List<Handler> handlerChain = binding.getHandlerChain();
        handlerChain.add(this);
        binding.setHandlerChain(handlerChain);
        return port;
    }

    protected P remote() {
        return port;
    }

    protected abstract S getService();

    protected abstract P getPort(S service);

    ////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        log(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        log(context);
        return true;
    }

    @Override
    public void close(MessageContext context) {}

    private void log(SOAPMessageContext context) {
        System.out.printf("Message : %s%n", stringify(context));
    }

    private String stringify(SOAPMessageContext context) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            context.getMessage().writeTo(bout);
            return bout.toString("UTF-8");
        } catch (SOAPException e) {
            throw new RuntimeException("SOAP Exception !", e);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception !", e);
        }

    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
}
