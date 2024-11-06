import type { Actions } from './$types';
import { fail } from '@sveltejs/kit';

export const actions = {
	createAction: async ({ request, fetch }) => {
		const data = await request.formData();
		const newActionText = data.get('action-text');
		const response = await fetch('/api/next-action', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				action: newActionText
			})
		});
		if (!response.ok) {
			return fail(response.status, {
				'action-text': newActionText
			});
		}
		const newAction = await response.json();
		return {
			success: true,
			newAction
		};
	},
	updateAction: async ({ request, fetch }) => {
		const data = await request.formData();
		const id = data.get('id');
		const response = await fetch(`/api/next-action/complete/${id}`, {
			method: 'PATCH',
			headers: {
				'Content-Type': 'application/json'
			}
		});
		if (response.status !== 200) {
			return fail(response.status);
		}
		return {
			success: true
		};
	}
} satisfies Actions;
