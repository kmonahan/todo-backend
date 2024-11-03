<script lang="ts">
	import type { ActionData, PageData } from './$types';
	import { enhance } from '$app/forms';

	const { data, form }: { data: PageData; form: ActionData } = $props();
</script>

<h1>To-Dos</h1>

<form method="POST" action="?/createAction" use:enhance>
	<label for="add-next-action">Next Action</label>
	<input type="text" id="add-next-action" name="action-text" />
	<button type="submit">Add</button>
</form>
{#if form?.success}Added!{/if}

<h2>Your Next Actions ({data.actions.length})</h2>
<ul>
	{#each data.actions as nextAction}
		<li>{nextAction.action}</li>
	{/each}
</ul>
