package br.com.jtaskmanager.view.report;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.jtaskmanager.dto.CategoriaTreeDTO;

public class CategoriaTreeModel {

	public static DefaultTreeModel getTree(List<CategoriaTreeDTO> categorias) {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categorias");
		if (!categorias.isEmpty()) {
			String categoria = categorias.get(0).getCategoria();
			String tarefa = categorias.get(0).getTarefa();

			DefaultMutableTreeNode categoriaNode = new DefaultMutableTreeNode(categoria);
			DefaultMutableTreeNode tarefaNodeNode = new DefaultMutableTreeNode(tarefa);
			tarefaNodeNode.add(new DefaultMutableTreeNode(categorias.get(0).getCriterio()));

			if (1 == categorias.size()) {
				categoriaNode.add(tarefaNodeNode);
				root.add(categoriaNode);
			}

			for (int i = 1; i < categorias.size(); i++) {
				if (categorias.get(i).getCategoria().equalsIgnoreCase(categoria)) {
					if (categorias.get(i).getTarefa().equalsIgnoreCase(tarefa)) {
						tarefaNodeNode.add(new DefaultMutableTreeNode(categorias.get(i).getCriterio()));
					} else {
						categoriaNode.add(tarefaNodeNode);
						tarefaNodeNode = new DefaultMutableTreeNode(categorias.get(i).getTarefa());
						tarefaNodeNode.add(new DefaultMutableTreeNode(categorias.get(i).getCriterio()));
					}
				} else {
					categoriaNode.add(tarefaNodeNode);
					root.add(categoriaNode);
					categoriaNode = new DefaultMutableTreeNode(categorias.get(i).getCategoria());
					tarefaNodeNode = new DefaultMutableTreeNode(categorias.get(i).getTarefa());
					tarefaNodeNode.add(new DefaultMutableTreeNode(categorias.get(i).getCriterio()));
				}
				if ((i + 1) == categorias.size()) {
					categoriaNode.add(tarefaNodeNode);
					root.add(categoriaNode);
				}

				categoria = categorias.get(i).getCategoria();
				tarefa = categorias.get(i).getTarefa();
			}
		}
		return new DefaultTreeModel(root);
	}

}
