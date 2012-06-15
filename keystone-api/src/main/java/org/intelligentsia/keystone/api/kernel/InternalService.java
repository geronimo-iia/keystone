package org.intelligentsia.keystone.api.kernel;

/**
 * 
 * InternalService.
 * 
 * 
 ** <p>
 * An internal server—also known as a subsystem extends the functionality
 * provided by the microkernel. It represents a separate component that offers
 * additional functionality. The microkernel invokes the functionality of
 * internal servers via service requests. Internal servers can therefore
 * encapsulate some dependencies on the underlying hardware or software system.
 * For example, device drivers that support speciﬁc graphics cards are good
 * candidates for internal servers.
 * </p>
 * <p>
 * One of the design goals should be to keep the microkernel as small as
 * possible to reduce memory requirements. Another goal is to provide mechanisms
 * that execute quickly, to reduce service execution time. Additional and more
 * complex services are therefore implemented by internal servers that the
 * microkernel activates or loads only when necessary. You can consider internal
 * servers as extensions of the microkernel. Note that internal servers are only
 * accessible by the microkernel component.
 * </p>
 * <p>
 * Responsibility
 * </p>
 * <ul>
 * <li>Implements additional services</li>
 * <li>Encapsulates some system specifics.</li>
 * </ul>
 * </p>
 * <p>
 * Collaborators
 * </p>
 * Microkernel </p>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface InternalService extends Service {

}
