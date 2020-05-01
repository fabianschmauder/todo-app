import {
    ADD_TODO,
    ADD_TODO_FAILED,
    ADD_TODO_SUCCESS, DELETE_TODO, DELETE_TODO_FAILED, DELETE_TODO_SUCCESS,
    FETCH_TODOS,
    FETCH_TODOS_FAILED,
    FETCH_TODOS_SUCCESS, SET_TODO_STATUS, SET_TODO_STATUS_FAILED, SET_TODO_STATUS_SUCCESS
} from "./todo-actions";

export const todoContextReducer = (state, action) => {
    switch (action.type) {
        case FETCH_TODOS:
            return {...state, fetch: 'RUNNING'};
        case FETCH_TODOS_SUCCESS:
            return {...state, todos: action.todos, fetch: 'SUCCESS'};
        case FETCH_TODOS_FAILED:
            return {...state, fetch: 'FAILED'};
        case ADD_TODO:
            return {...state, adding: 'RUNNING'};
        case ADD_TODO_SUCCESS:
            return {...state, todos: [...state.todos, action.todo], adding: 'SUCCESS'};
        case ADD_TODO_FAILED:
            return {...state, adding: 'FAILED'};
        case DELETE_TODO:
            return {
                ...state,
                todos: state.todos.map(todo => todo.id === action.id ? ({...todo, deleting: 'RUNNING'}) : todo)
            };
        case DELETE_TODO_SUCCESS:
            return {...state, todos: state.todos.filter(todo => todo.id !== action.id)};
        case DELETE_TODO_FAILED:
            return {
                ...state,
                todos: state.todos.map(todo => todo.id === action.id ? ({...todo, deleting: 'FAILED'}) : todo)
            };
        case SET_TODO_STATUS:
            return {
                ...state,
                todos: state.todos.map(todo => todo.id === action.id ? ({...todo, updating: 'RUNNING'}) : todo)
            };
        case SET_TODO_STATUS_SUCCESS:
            return {...state, todos: [...state.todos.filter(todo => todo.id !== action.todo.id), action.todo]};
        case SET_TODO_STATUS_FAILED:
            return {
                ...state,
                todos: state.todos.map(todo => todo.id === action.id ? ({...todo, updating: 'FAILED'}) : todo)
            };
        default:
            return state;
    }
};
