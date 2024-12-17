import React, { useEffect } from 'react';

const SvgHighlighter = ({ svgRef, activeComponents }) => {
    useEffect(() => {
        if (svgRef.current) {
            if (activeComponents.length === 0) {
                const svgElements = svgRef.current.querySelectorAll('[id]');
                svgElements.forEach((element) => {
                    element.style.stroke = "black";
                });
                return;
            }

            activeComponents.forEach(({ id, color }) => {
                const element = svgRef.current.querySelector(`#${id}`);
                if (element) {
                    element.style.stroke = color;
                }
            });
        }
    }, [svgRef, activeComponents]);

    return null;
};

export default SvgHighlighter;
