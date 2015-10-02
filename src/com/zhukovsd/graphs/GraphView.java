package com.zhukovsd.graphs;

import java.awt.*;

/**
 * Representation of {@link Graph graph}, which can be painted on java.awt.Graphic2D object.
 * @param <T> type of represented {@link Graph graph}
 * @param <U> type of {@link Vertex vertex} of represented {@link Graph graph}
 */
public abstract class GraphView<T extends Graph<U>, U extends Vertex> {
    /**
     * {@link Graph Graph} object, represented by current view. Immutable.
     */
    private final T graph;

    /**
     * Create graph representation of given {@link Graph graph}
     * @param graph graph to represent
     */
    public GraphView(T graph) {
        this.graph = graph;
    }

    /**
     * Getter for typed graph, may be overrode if graph have to be specific of specific type, derived from T.
     * @return stored graph to represent
     */
    protected T getGraph() {
        return graph;
    }

    /**
     * Abstract method for calculating size of image, representing current view. Must be overrode in derived class.
     * @return image size as {@link Point} object
     */
    public abstract Point getSize();

    /**
     * Abstract method for calculating vertex position in current view representation. Must be overrode in derived class.
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    public abstract Point calculateVertexPosition(U vertex);

    /**
     * Paint given vertex on given {@link Graphics2D graphics} object.
     * @param graphics graphics object to paint on
     * @param vertex {@link Vertex vertex} object to paint
     */
    protected void paintVertex(Graphics2D graphics, U vertex) {
        Point position = calculateVertexPosition(vertex);
        graphics.drawLine(position.x, position.y, position.x, position.y);
    }

    /**
     * Paint given link on given {@link Graphics2D graphics} object.
     * @param graphics graphics object to paint on
     * @param edge {@link Edge edge} object to paint
     */
    protected void paintEdge(Graphics2D graphics, Edge edge) {
        Point sourcePosition = calculateVertexPosition((U) edge.destination);
        Point destinationPosition = calculateVertexPosition((U) edge.source);

        graphics.drawLine(sourcePosition.x, sourcePosition.y, destinationPosition.x, destinationPosition.y);
    }

    /**
     * Paint whole view on given {@link Graphics2D graphics} object with given color and stroke.
     * @param graphics graphics object to paint on
     * @param color color which will be used for painting
     * @param stroke stroke which will be used for painting
     */
    public void paint(Graphics2D graphics, Color color, Stroke stroke) {
        graphics.setColor(color);
        graphics.setStroke(stroke);

        for (U vertex : graph.vertexList) {
            paintVertex(graphics, vertex);

            for (Edge edge : vertex.edgeList) {
                paintEdge(graphics, edge);
            }
        }
    }

    /**
     * Paint whole view on given {@link Graphics2D graphics} object with black color and 1 pixel width stroke
     * @param graphics graphics object to paint on
     */
    public void paint(Graphics2D graphics) {
        paint(graphics, Color.BLACK, new BasicStroke(1));
    }
}