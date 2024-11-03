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
{#if form?.success}
	<p>Good job!</p>
	{#if form?.newAction}
		<p>Added {form.newAction.action}</p>
	{/if}
{/if}

<h2>Your Next Actions ({data.actions.length})</h2>
