package graph

import com.mxgraph.swing.handler.mxKeyboardHandler
import com.mxgraph.swing.handler.mxRubberband
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants
import com.mxgraph.view.mxGraph
import com.mxgraph.view.mxStylesheet
import java.util.*

@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")

open class Graph{
    var graphComponent:mxGraphComponent?=null
    protected  val vertex = VertexProperties()

    var graph = mxGraph()
    var initialState:String?=null
    var parent: Any? = graph.defaultParent

    init{
        graph.model.beginUpdate()
        graph.model.endUpdate()

        graphComponent = mxGraphComponent(graph)
        graph.isMultigraph = false
        graph.isAllowDanglingEdges = false
        graphComponent!!.isConnectable = false
        graphComponent!!.setToolTips(true)
        applyEdgeDefaults()

        mxRubberband(graphComponent)
        mxKeyboardHandler(graphComponent)
        applyEdgeDefaults()
}
    private fun getRandomPosition():Double{
        val range = (700 - 20) + 1
        return (Math.random() * range) + 20
    }

    fun AddState(state:StateProperties){
        var style:String?=null
        if(state.isAcceptationState&&state.isInitialState)
            style = vertex.acceptedAndInitialStateStyle
        else if(state.isAcceptationState)
            style = vertex.acceptedStateStyle
        else if(state.isInitialState)
            style = vertex.initialStateStyle
        else
            style = vertex.stateStyle
        graph.insertVertex(parent, null, state.stateId, getRandomPosition(), getRandomPosition()
                , vertex.height, vertex.width,style)
        graph.refresh()
    }

    fun AddTransition(from:String, to:String, id:String){
        try {
            val fromPos = getStatePosition(from)
            val toPos = getStatePosition(to)
            updateCells()
            val cells = graph.selectionCells
            graph.insertEdge(parent, null, id, cells[fromPos], cells[toPos])
            graph.refresh()
        }catch(e:UINotFoundStateException){
//            println(e.message)
        }
    }

    protected fun getStatePosition(stateId: String):Int{
        updateCells()
        val cells = graph.selectionCells
        var value:String?=null
        for(x in 0..(cells.size -1)){
            val property = cells[x].javaClass.getDeclaredField("value")
            property.isAccessible = true
            value = property.get(cells[x]).toString()
            if(value.equals(stateId)){
                return x
            }
        }
        throw UINotFoundStateException("Not found state with value: "+stateId)
    }

    fun turnOnState(stateId: String) {
        changeStateStyle(stateId,vertex.initialStateStyle)
    }

    fun turnOffState(stateId: String){
        changeStateStyle(stateId,vertex.stateStyle);
    }

    private fun changeStateStyle(stateId: String, style: String){
        try{
            val pos = getStatePosition(stateId)
            updateCells()
            val cells = graph.selectionCells
            val state = cells[pos]
            val property = state.javaClass.getDeclaredField("style")
            property.isAccessible = true
            val previousStyle = property.get(state)
            var newStyle:String?=null
            if(previousStyle.equals(vertex.acceptedStateStyle)&&style.equals(vertex.initialStateStyle))
                newStyle = vertex.acceptedAndInitialStateStyle
            else if(previousStyle.equals(vertex.acceptedAndInitialStateStyle)&&style.equals(vertex.stateStyle))
                newStyle = vertex.acceptedStateStyle
            else
                newStyle = style

            property.set(state,newStyle)
            initialState = stateId
            graph.refresh()
        }catch(e:UINotFoundStateException){
            println(e.message)
        }

    }

    protected  fun updateCells(){
        graph.clearSelection()
        graph.selectVertices()
    }

    fun GetStates():Array<String>?{
        updateCells()
        val cells = graph.selectionCells
        var value:String?=null
        val states = ArrayList<String>()
        for(x in 0..(cells.size -1)){
            val property = cells[x].javaClass.getDeclaredField("value")
            property.isAccessible = true
            value = property.get(cells[x]).toString()
            if(!value.isNullOrEmpty())
                states.add(value)
        }
        val _states = Array(states.size, { i -> states.elementAt(i) })
        return _states
    }

//    fun getStateProperties(_state:State):StateProperties{
//        val sp = StateProperties()
//        sp.stateId = _state.id
//        sp.isAcceptationState = _state.isAcceptationState
//        sp.isInitialState = _state.isInitialState
//        return sp
//    }

//    fun SetGraph(_states:ArrayList<State>){
//        graph.removeCells(graph.getChildVertices(graph.defaultParent))
//        graph.refresh()
//        for(x in 0..(_states.size-1)){
//            AddState(getStateProperties(_states[x]))
//        }
//        for(statePos in 0..(_states.size-1)){
//            for(transitionPos in 0..(_states[statePos].transitions.size-1)){
//                val trans = _states[statePos].transitions[transitionPos]
//                AddTransition(trans.from!!,trans.to!!,trans.id!!)
//            }
//        }
//    }

    fun Clear(){
        graph.removeCells(graph.getChildVertices(graph.defaultParent))
        graph.refresh()
    }

    fun RemoveTheLastInitialState(stateId: String){
        changeStateStyle(stateId,vertex.stateStyle)
    }

    private fun applyEdgeDefaults() {
        val edge = HashMap<String, Any>()
        edge.put(mxConstants.STYLE_ROUNDED, true)
        edge.put(mxConstants.STYLE_ORTHOGONAL, false)
        edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle")
        edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR)
        edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC)
        edge.put(mxConstants.STYLE_STROKECOLOR, "#6482B9") // default is #6482B9

        edge.put(mxConstants.STYLE_FONTCOLOR, "#446299")

        graph.isAllowLoops = true
        graph.isAllowDanglingEdges = false
        graph.isEdgeLabelsMovable = false
        graph.isCellsEditable = true
        graph.isConnectableEdges = true

        edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE)
        edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT)

        val edgeStyle = mxStylesheet()
        edgeStyle.defaultEdgeStyle = edge
        graph.stylesheet = edgeStyle
    }
}