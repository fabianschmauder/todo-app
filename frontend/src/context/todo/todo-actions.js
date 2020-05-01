import {addTodo, getTodos, deleteTodo, setTodoStatus} from "../../service/todo-service";

export const FETCH_TODOS = 'FETCH_TODOS';
export const FETCH_TODOS_SUCCESS = 'FETCH_TODOS_SUCCESS';
export const FETCH_TODOS_FAILED = 'FETCH_TODOS_FAILED';

const fetchItems = async (dispatch) => {
    dispatch({type: FETCH_TODOS});
    try {
        const response = await getTodos();
        dispatch({type: FETCH_TODOS_SUCCESS, todos: response.data});
    } catch (error) {
        dispatch({type: FETCH_TODOS_FAILED, error});
    }
};

export const ADD_TODO = 'ADD_TODO';
export const ADD_TODO_SUCCESS = 'ADD_TODO_SUCCESS';
export const ADD_TODO_FAILED = 'ADD_TODO_FAILED';

const addItem = async (dispatch, description) => {
    dispatch({type: ADD_TODO});
    try {
        const response = await addTodo(description);
        dispatch({type: ADD_TODO_SUCCESS, todo: response.data});
    } catch (error) {
        dispatch({type: ADD_TODO_FAILED, error});
    }
};

export const DELETE_TODO = 'DELETE_TODO';
export const DELETE_TODO_SUCCESS = 'DELETE_TODO_SUCCESS';
export const DELETE_TODO_FAILED = 'DELETE_TODO_FAILED';

const deleteItem = async (dispatch, id) => {
    dispatch({type: DELETE_TODO, payload: id});
    try {
        await deleteTodo(id);
        dispatch({type: DELETE_TODO_SUCCESS, id});
    } catch (error) {
        dispatch({type: DELETE_TODO_FAILED, error});
    }
};

export const SET_TODO_STATUS = 'SET_TODO_STATUS';
export const SET_TODO_STATUS_SUCCESS = 'SET_TODO_STATUS_SUCCESS';
export const SET_TODO_STATUS_FAILED = 'SET_TODO_STATUS_FAILED';

const setItemStatus = async (dispatch, id, status) => {
    dispatch({type: SET_TODO_STATUS, payload: {id, status}});
    try {
        const response = await setTodoStatus(id, status);
        dispatch({type: SET_TODO_STATUS_SUCCESS, todo: response.data});
    } catch (error) {
        dispatch({type: SET_TODO_STATUS_FAILED, error});
    }
};

export {
    fetchItems,
    addItem,
    deleteItem,
    setItemStatus
};
