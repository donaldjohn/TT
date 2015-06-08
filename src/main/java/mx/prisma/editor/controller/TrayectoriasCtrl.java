package mx.prisma.editor.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import mx.prisma.editor.model.Trayectoria;
import mx.prisma.util.ActionSupportPRISMA;


@ResultPath("/content/editor/")
@Results({ @Result(name = ActionSupportPRISMA.SUCCESS, type = "redirectAction", params = {
		"actionName", "trayectorias" })
})
public class TrayectoriasCtrl extends ActionSupportPRISMA{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Trayectoria model; 

	public HttpHeaders index() throws Exception{
		
		return new DefaultHttpHeaders(INDEX);
	}

	public Trayectoria getModel() {
		return model;
	}

	public void setModel(Trayectoria model) {
		this.model = model;
	}
	
	
}
