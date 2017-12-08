package graph
import com.mxgraph.view.mxGraph
import java.util.ArrayList

class GraphParser(): Graph() {

   /* fun ParserGraphToAutomaton(graph: mxGraph): ArrayList<State> {
        this.graph = graph
        val arr = ArrayList<State>()
        val _states = GetStates()
        for(x in 0..(_states!!.size-1)) {
            arr.add(getState(_states.elementAt(x)))
        }
        return arr
    }

    private fun getTransitionsOfState(stateId: String):ArrayList<Transition>{
        val transitions = ArrayList<Transition>()
        val pos = getStatePosition(stateId)
        updateCells()
        val cells = graph.selectionCells
        val state = cells[pos]
        val edges = graph.getEdges(state)
        for(x in 0..(edges.size-1)){
            val source = edges[x].javaClass.getDeclaredField("source")
            source.isAccessible = true
            val sourceCell = source.get(edges[x])
            val sourceValue = sourceCell.javaClass.getDeclaredField("value")
            sourceValue.isAccessible = true
            val _srcValue = sourceValue.get(sourceCell).toString()
            if(_srcValue.equals(stateId)){
                val trans = Transition()
                val id = edges[x].javaClass.getDeclaredField("value")
                id.isAccessible = true
                trans.id = id.get(edges[x]).toString()
                trans.from = stateId
                val target = edges[x].javaClass.getDeclaredField("target")
                target.isAccessible = true
                val targetCell = target.get(edges[x])
                val targetValue = targetCell.javaClass.getDeclaredField("value")
                targetValue.isAccessible = true
                trans.to = targetValue.get(targetCell).toString()
                transitions.add(trans)
            }
        }
        return transitions
    }

    private fun getState(stateId: String):State{
        val _state = State()
        _state.transitions = getTransitionsOfState(stateId)
        val pos = getStatePosition(stateId)
        updateCells()
        val cells = graph.selectionCells
        val state = cells[pos]
        val style = state.javaClass.getDeclaredField("style")
        style.isAccessible = true
        val _style = style.get(state)
        _state.isAcceptationState = if(_style.equals(vertex.acceptedStateStyle)||
                _style.equals(vertex.acceptedAndInitialStateStyle)) true else false
        _state.isInitialState = if(_style.equals(vertex.initialStateStyle)||
                _style.equals(vertex.acceptedAndInitialStateStyle)) true else false
        _state.id = stateId
        return _state
    } */
}