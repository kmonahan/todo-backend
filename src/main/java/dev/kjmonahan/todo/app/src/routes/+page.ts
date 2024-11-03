import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
    const response = await fetch(`/api/next-action`);
    const actions = await response.json();
    return {actions}
};