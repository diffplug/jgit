package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Implementation of git cherry-pick, but only the non-committing king
 */
@Command(common = true, usage = "usage_cherryPick")
public class CherryPick extends TextBuiltin {
	@Argument(required = true, metaVar = "metaVar_commitish")
	private List<String> commits = new ArrayList<String>();

	@Option(name = "--no-commit", aliases = { "-n" }, usage = "usage_NoCommit")
	private boolean noCommit;

	@Override
	protected void run() {
		Git git = new Git(db);
		CherryPickCommand cherryPick = git.cherryPick();
		for (String commit : commits) {
			cherryPick.include(ObjectId.fromString(commit));
		}
		cherryPick.setNoCommit(noCommit);

		try {
			cherryPick.call();
		} catch (Exception e) {
			throw die(e.getMessage(), e);
		}
	}
}