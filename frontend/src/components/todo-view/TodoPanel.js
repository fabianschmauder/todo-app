import React from "react";
import {TodoCard} from "./TodoCard";
import Typography from "@material-ui/core/Typography";

export const TodoPanel = ({items, status}) => {
    return <div style={{display:'flex', flexDirection:'column', flexGrow: 1}}>
        <Typography variant="h5" component="h2" style={{textAlign: 'center'}}>
            {mapStatus(status)}
        </Typography>
        <div style={{backgroundColor: '#f4f4f4', padding: '1rem', flexGrow: 1}}>
            {items.filter(item => item.status === status)
                .map(item => <TodoCard key={item.id} item={item}/>)}
        </div>
    </div>
};

const mapStatus = (status) => {
    switch (status) {
        case 'OPEN':
            return 'Open';
        case 'IN_PROGRESS':
            return 'In Progress';
        case 'DONE':
            return 'Done';
        default:
            return status;
    }
};
