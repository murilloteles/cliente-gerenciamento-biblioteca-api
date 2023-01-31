package com.telesdev.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.telesdev.controller.FuncionarioBean;
import com.telesdev.model.Endereco;

@FacesConverter(value = "enderecoConverter")
public class EnderecoConverter  implements Converter{

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String enderecoId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{funcionarioBean}", FuncionarioBean.class);

        FuncionarioBean funcionarioBean = (FuncionarioBean)vex.getValue(ctx.getELContext());
        return funcionarioBean.getEndereco(Long.valueOf(enderecoId));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object endereco) {
    	return endereco != null && ((Endereco)endereco).getId() != null ? ((Endereco)endereco).getId().toString() : "";
    }
}