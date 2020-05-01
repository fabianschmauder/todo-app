import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import Card from "@material-ui/core/Card/Card";
import React from "react";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import {useTodoDispatch} from "../../context/todo/todo-selector";
import {deleteItem, setItemStatus} from "../../context/todo/todo-actions";

export const TodoCard = ({item}) => {
    const dispatch = useTodoDispatch();

    return <Card style={{margin: '0.2rem'}}>
        <CardContent>
            <Typography variant="body2" component="p">
                {item.description}
            </Typography>
        </CardContent>
        <CardActions>
            <Button size="small" onClick={() => deleteItem(dispatch, item.id)}>Delete</Button>
            {item.status === 'OPEN' &&
            <Button size="small" onClick={() => setItemStatus(dispatch, item.id, 'IN_PROGRESS')}>Progress</Button>}
            {item.status === 'IN_PROGRESS' &&
            <Button size="small" onClick={() => setItemStatus(dispatch, item.id, 'DONE')}>Done</Button>}
        </CardActions>
    </Card>
};
