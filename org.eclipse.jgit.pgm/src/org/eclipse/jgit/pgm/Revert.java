package org.eclipse.jgit.pgm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RevertCommand;
import org.eclipse.jgit.lib.ObjectId;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Implementation of git cherry-pick, but only the non-committing king
 */
@Command(common = true, usage = "usage_revert")
public class Revert extends TextBuiltin {
	@Argument(required = true, metaVar = "metaVar_commitish")
	private List<String> commits = new ArrayList<String>();

	@Option(name = "--no-commit", aliases = { "-n" }, usage = "usage_NoCommit")
	private boolean noCommit;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			RevertCommand revert = git.revert();
			for (String commit : commits) {
				revert.include(ObjectId.fromString(commit));
			}
			revert.setNoCommit(noCommit);

			try {
				revert.call();
			} catch (Exception e) {
				throw die(e.getMessage(), e);
			}
		}
	}
}
