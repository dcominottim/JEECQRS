/*
 * Copyright (c) 2013 Red Rainbow IT Solutions GmbH, Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.jeecqrs.command.registry.autodiscover;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.jeecqrs.command.registry.AbstractCommandHandlerRegistry;

/**
 * Automatically discovers command handlers via CDI injection.
 * @param <C> the command base type to look up.
 */
public class AutoDiscoverCommandHandlerRegistry<C> extends AbstractCommandHandlerRegistry<C> {

    private Logger log = Logger.getLogger(AutoDiscoverCommandHandlerRegistry.class.getName());

    @Inject
    private Instance<AutoDiscoveredCommandHandler<? extends C>> handlerInstances;

    @PostConstruct
    public void startup() {
        log.info("Scanning command handlers...");
	Iterator<AutoDiscoveredCommandHandler<? extends C>> it = select(handlerInstances);
        if (!it.hasNext())
            log.warning("No command handlers found.");
	while (it.hasNext())
            this.register(it.next());
    }

    protected <H extends C> void register(AutoDiscoveredCommandHandler<H> handler) {
        Class<H> commandType = handler.handledCommandType();
        log.log(Level.FINE, "Discovered command handler {0} for command {1}",
                new Object[]{handler.getClass(), commandType.getSimpleName()});
        this.register(commandType, handler);
    }

    protected Iterator<AutoDiscoveredCommandHandler<? extends C>> select(
            Instance<AutoDiscoveredCommandHandler<? extends C>> instances) {
        return instances.iterator();
    } 
    
}
