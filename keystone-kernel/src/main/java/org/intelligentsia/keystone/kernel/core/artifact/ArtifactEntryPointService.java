package org.intelligentsia.keystone.kernel.core.artifact;

import org.intelligentsia.keystone.kernel.KernelContext;
import org.intelligentsia.keystone.kernel.event.ArtifactContextChangeEvent;
import org.intelligentsia.keystone.kernel.service.Service;

// may we move this in another jar or integrate this functionnality to a basic ArtifacServer with an ArtifactHandler 
public class ArtifactEntryPointService implements Service {

	// may we add Kernel instance
	public ArtifactEntryPointService() {
		super();
	}

	@Override
	public String getName() {
		return "ArtifactEntryPoint";
	}

	@Override
	public void initialize(KernelContext context) {
	}

	@Override
	public void destroy() {
	}

	public void onArtifactContextChange(ArtifactContextChangeEvent artifactContextChangeEvent) {

	}
}
