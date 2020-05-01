const axios = require('axios');

export const getTodos = async () => {
    return await axios.get('/api/todo');
};

export const addTodo = async (description) => {
    return await axios.post('/api/todo', {id: 1, status: 'OPEN', description});
};

export const deleteTodo = async (id) => {
    return await axios.delete(`/api/todo/${id}`);
};

export const setTodoStatus = async (id, status) => {
    return await axios.post(`/api/todo/${id}/status`, {status});
};

